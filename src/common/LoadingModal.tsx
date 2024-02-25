import { Watch } from 'react-loader-spinner';
import './LoadingModal.css';

interface OverlayProps {
    isLoading: boolean;
}

export default function LoadingModal(props: OverlayProps) {
    return (
        <>
            {props.isLoading && (
                <div className="overlay">
                    <div className="modal">
                        <Watch
                            visible={props.isLoading}
                            height="120"
                            width="120"
                            radius="48"
                            color="white"
                            ariaLabel="watch-loading"
                        />
                    </div>
                </div>
            )}
        </>
    );
};